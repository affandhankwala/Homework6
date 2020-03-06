import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMiniSearchEngine {
    // default solution. OK to change.
    // do not change the signature of index()
    private Map<String, List<List<Integer>>> indexes = new HashMap<>(); //Need to initialize this
    // private Map<String, List<Integer>> indexes = new HashMap<>();
    // disable default constructor
    private MyMiniSearchEngine() {
    }

    public MyMiniSearchEngine(List<String> documents) {
        index(documents);
    }

    // each item in the List is considered a document.
    // assume documents only contain alphabetical words separated by white spaces.
    private void index(List<String> texts) {    //This list is bunch of sentences that is being passed in and that sentence has all words we need.

        for(int i = 0; i < texts.size(); i++){
            String[] words = texts.get(i).split(" "); //this splits each sentence into array of words.
            for(int j = 0; j < words.length; j++){
               words[j] = strip(words[j]);
                if(indexes.containsKey(words[j])){ //If the word is already a key then we wish to add the document as part of the linkedlist.
                    List<List<Integer>> value = indexes.get(words[j]);
                    value.get(i).add(j);

                }
                else{
                    List<List<Integer>> temp = new ArrayList<List<Integer>>();
                    for(int l = 0; l < texts.size(); l++){
                      temp.add(new ArrayList<>());
                    }
                    temp.get(i).add(j);
                    indexes.put(words[j], temp);

                }
            }
        }

    }

    // search(key) return all the document ids where the given key phrase appears.
    // key phrase can have one or two words in English alphabetic characters.
    // return an empty list if search() finds no match in all documents.
    public List<Integer> search(String keyPhrase) {
        //safety check
        if(keyPhrase.isEmpty() || indexes.isEmpty())
            return new ArrayList<>();

        String[] words= keyPhrase.split(" ");
        words[0] = strip(words[0]);
        if(!indexes.containsKey(words[0]))
            return new ArrayList<>();
        List<List<Integer>> result = clone(indexes.get(words[0]));
        for(int i = 1; i < words.length; i++) {
            words[i] = strip(words[i]);
            if(!indexes.containsKey(words[i]))
                return new ArrayList<>();
            List<List<Integer>> temp = clone(indexes.get(words[i]));   //This is the values of all the next word.
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j).isEmpty()) {
                    temp.get(j).clear();
                } else {
                    for (int k = 0; k < temp.get(j).size(); k++) {
                        int l = 0;
                        while (result.get(j).get(l) < temp.get(j).get(k)) {
                            l++;
                            if(l>=result.get(j).size())
                                break;
                        }
                        if(l > 0) --l;
                        if (result.get(j).get(l) != (temp.get(j).get(k) - 1)) {
                            temp.get(j).remove(k);
                            k--;
                        }
                    }
                }
            }
            result = temp;
        }
        List<Integer> answer = new ArrayList<>();

        for(int i = 0; i < result.size(); i++){
            if(!result.get(i).isEmpty()){
                answer.add(i);
            }
        }

       return answer;

    }
    public static String strip (String string){
        string = string.toLowerCase();
        string = string.replaceAll("[^a-z]", "");
        return string;
    }
    public static void printResult(List<List<Integer>> result){         //Used printResult for testing purposes
        for(List<Integer> document: result){
            System.out.print(" - ");
            for(Integer position : document){
                System.out.print (position+" ");
            }
            System.out.println();
        }
    }
    public static List<List<Integer>> clone(List<List<Integer>> source) {           //This is a deep copy of the list.
        List<List<Integer>> target = new ArrayList<List<Integer>>();
        for (int i = 0; i < source.size(); ++i)
            target.add(new ArrayList<Integer>(source.get(i)));

        return target;
    }

}
