public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> nodes = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++){
            nodes.addLast(word.charAt(i));
        }
        return nodes;
    }

    public boolean isPalindrome(String word){
        Deque<Character> words = wordToDeque(word);
        return helperIsPalindrome(words);
    }

    private boolean helperIsPalindrome(Deque<Character> nodes){
        if (nodes.size() < 2){
            return true;
        }else{
            Character left = nodes.removeFirst();
            Character right = nodes.removeLast();
            if (left != right){
                return false;
            }
            return helperIsPalindrome(nodes);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> words = wordToDeque(word);
        return helperIsPalindrome2(words, cc);
    }
    private boolean helperIsPalindrome2(Deque<Character> nodes, CharacterComparator cc){
        if (nodes.size() < 2){
            return true;
        }else{
            Character left = nodes.removeFirst();
            Character right = nodes.removeLast();
            if (!(cc.equalChars(left, right))){
                return false;
            }
            return helperIsPalindrome2(nodes, cc);
        }
    }
}
