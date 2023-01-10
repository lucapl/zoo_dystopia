package ZooDystopia.Utils.Searching;

//
//public abstract class DepthFirst implements Search{
//    public void search(Map map){
//        int n = map.getStructures().size();
//
//        boolean[] visited = new boolean[n];
//        for(int i = 0; i < n; i++){
//            visited[i] = false;
//        }
//
//        for(int i = 0; i < n; i++){
//            Structure structure = map.getStructures().get(i);
//            if(!visited[i]) {
//                dfs(structure);
//            }
//        }
//    }
//    private void dfs(Node node){
//        for(Node child: node.getChildren()){
//            dfs(child);
//        }
//    }
//}
