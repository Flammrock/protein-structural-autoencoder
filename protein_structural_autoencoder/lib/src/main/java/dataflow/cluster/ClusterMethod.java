package dataflow.cluster;

import java.util.List;

public enum ClusterMethod {

	KMeans() {
        @Override
        public List<ClusterPoint<?>> clusterize(List<ClusterPoint<?>> points) { 
             // implement K-Means here
        	return null;
        }
   }
;
   
   public abstract List<ClusterPoint<?>> clusterize(List<ClusterPoint<?>> points);

}
