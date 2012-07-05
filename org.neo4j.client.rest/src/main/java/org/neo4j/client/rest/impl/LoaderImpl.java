/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.net.URI;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.util.PathUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * @author Ricker
 * 
 */
public class LoaderImpl implements Loader {

	private Log log = LogFactory.getLog(Loader.class);

	private ObjectMapper mapper;
	private HttpClient httpclient;
	private URI uri;
	private DatabaseData data;

	public LoaderImpl(URI uri) {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		httpclient = new DefaultHttpClient(cm);
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setUri(uri);
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
		if (uri != null) {
			loadDatabaseData();
		} else {
			data = null;
		}
	}

	public String createNodeUri(long id) {
		return data.getNode() + "/" + Long.toString(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public String createRelationshipUri(long id) {
		/*
		 * Why is relationship sent as part of the database data?
		 */
		return uri.toString() + "/relationship/" + Long.toString(id);
	}

	private void loadDatabaseData() {
		try {
			HttpGet request = new HttpGet(uri);
			request.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(request);
			data = mapper.readValue(response.getEntity().getContent(), DatabaseData.class);
			EntityUtils.consume(response.getEntity());
		} catch (Exception e) {
			log.error("Could not load database data for " + uri, e);
		}
	}

	@Override
	public NodeData createNode() throws RestClientException {
		checkData();
		try {
			HttpPost request = new HttpPost(data.getNode());
			request.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(request);
			NodeData nodeData = mapper.readValue(response.getEntity().getContent(), NodeData.class);
			EntityUtils.consume(response.getEntity());
			return nodeData;
		} catch (Exception e) {
			throw new RestClientException(e);
		}
	}

	@Override
	public NodeData loadNode(long nodeId) throws RestClientException {
		checkData();
		String path = createNodeUri(nodeId);
		HttpGet request = new HttpGet(path);
		request.setHeader("Accept", "application/json");
		try {
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == 404) {
				return null;
			}
			NodeData nodeData = mapper.readValue(response.getEntity().getContent(), NodeData.class);
			EntityUtils.consume(response.getEntity());
			return nodeData;
		} catch (Exception e) {
			throw new RestClientException(e);
		}
	}

	@Override
	public Collection<RelationshipData> loadNodeRelationships(NodeData nodeData) throws RestClientException {
		checkData();
		try {
			HttpGet request = new HttpGet(nodeData.getAll_relationships());
			request.setHeader("Accept", "application/json");
			HttpResponse response = httpclient.execute(request);
			Collection<RelationshipData> nodeRelationships = mapper.readValue(response.getEntity().getContent(),
					new TypeReference<Collection<RelationshipData>>() {
					});
			EntityUtils.consume(response.getEntity());
			return nodeRelationships;
		} catch (Exception e) {
			throw new RestClientException(e);
		}
	}

	@Override
	public long getReferenceNodeId() throws RestClientException {
		checkData();
		return PathUtil.getNodeId(data.getReferenceNode());
	}

	@Override
	public RelationshipData loadRelationship(long id) throws RestClientException {
		checkData();
		String path = createRelationshipUri(id);
		HttpGet request = new HttpGet(path);
		request.setHeader("Accept", "application/json");
		try {
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == 404) {
				return null;
			}
			RelationshipData relationshipData = mapper.readValue(response.getEntity().getContent(),
					RelationshipData.class);
			EntityUtils.consume(response.getEntity());
			return relationshipData;
		} catch (Exception e) {
			throw new RestClientException(e);
		}
	}

	private void checkData() throws RestClientException {
		if (data == null) {
			throw new RestClientException("No database specified or available.");
		}
	}

	@Override
	public void deleteNode(NodeData node) throws RestClientException {
		HttpDelete request = new HttpDelete(node.getSelf());
		request.setHeader("Accept", "application/json");
		HttpResponse response;
		try {
			response = httpclient.execute(request);
			EntityUtils.consume(response.getEntity());
		} catch (Exception e) {
			throw new RestClientException(e);
		}
		if (response.getStatusLine().getStatusCode() != 204) {
			// TODO get message property from JSON
			throw new RestClientException(response.getStatusLine().getReasonPhrase());
		}
	}

	@Override
	public void saveNode(NodeData node) throws RestClientException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRelationship(RelationshipData relationship) throws RestClientException {
		HttpDelete request = new HttpDelete(relationship.getSelf());
		request.setHeader("Accept", "application/json");
		HttpResponse response;
		try {
			response = httpclient.execute(request);
			EntityUtils.consume(response.getEntity());
		} catch (Exception e) {
			throw new RestClientException(e);
		}
		if (response.getStatusLine().getStatusCode() != 204) {
			// TODO get message property from JSON
			throw new RestClientException(response.getStatusLine().getReasonPhrase());
		}
	}

	@Override
	public void saveRelationship(RelationshipData relationship) {
		// TODO Auto-generated method stub

	}

	@Override
	public RelationshipData createRelationship(NodeData start, NodeData end, String type)
			throws RestClientException {
		HttpPost request = new HttpPost(start.getCreate_relationship());
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-Type", "application/json");
		NewRelationshipData data = new NewRelationshipData(end.getSelf(), type);
		try {
			request.setEntity(new StringEntity(mapper.writeValueAsString(data)));
			HttpResponse response = httpclient.execute(request);
			RelationshipData relationshipData = mapper.readValue(response.getEntity().getContent(),
					RelationshipData.class);
			EntityUtils.consume(response.getEntity());
			return relationshipData;
		} catch (Exception e) {
			throw new RestClientException(e);
		}
	}

}
