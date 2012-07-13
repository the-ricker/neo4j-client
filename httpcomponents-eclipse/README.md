This project creates an Eclipse P2 repository of the Apache HTTP Components 
and the Jackson JSON projects. Needed for building Eclipse tools with Tycho 
Maven plugin.

This project is what Tycho project calls "pom first." None of the bundles exist 
as features in an Eclipse P2 repository, so there is no means to use Tycho 
dependencies management. Some must be converted into bundles, such as 
the *org.apache.commons.logging* project.

The ehcache and memcached projects are here wrapped as OSGi bundles, but are not 
currently a part of the build because they are not required, only optional.
