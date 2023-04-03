import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import javax.swing.border.Border;

public class Main extends JFrame implements ActionListener{

    JTextField userInput;
    //JButton userFinishedButton;
    eachWordPanel[] wordArray = new eachWordPanel[9];
    String theTargetWord = getTarget();

    int tmpMatchLetterCount=0;

    class eachWordPanel extends JPanel{
        JLabel[] wordInEachColum = new JLabel[5];
        public eachWordPanel(){
            this.setLayout(new GridLayout(1, 5));
            Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
            for(int i=0;i<5;i++){
                wordInEachColum[i] = new JLabel();
                wordInEachColum[i].setHorizontalAlignment(JLabel.CENTER);
                wordInEachColum[i].setOpaque(true);
                wordInEachColum[i].setBorder(blackBorder);
                this.add(wordInEachColum[i]);
            }
        }

        public void clearAPanel(){
            for(int y=0; y< wordInEachColum.length; y++){
                wordInEachColum[y].setText(null);
                wordInEachColum[y].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
        }
    }
    public Main(){

        this.setSize(300, 300);
        this.setTitle("Wordle Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(8, 2));
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        for(int x=0; x<5;x++){
            wordArray[x] = new eachWordPanel();
            this.add(wordArray[x]);
        }

        userInput = new JTextField(null, 0);
        userInput.addActionListener(this);
        this.add(userInput);

        JButton userFinishedButton = new JButton();
        userFinishedButton.setText("OK");
        this.add(userFinishedButton);
        userFinishedButton.addActionListener(this);
        this.revalidate();

        //this.add("You only have 5 attempts in this game, or you lose!");
        JOptionPane.showMessageDialog(null, "You have five attempts to find the right word, or you lose the game. Good Luck!", " The rules of the game", JOptionPane.INFORMATION_MESSAGE);

        System.out.println("Word of the day: "+theTargetWord);

    }
    //a list of all possible 5-letter words in English
    public static HashSet<String> dictionary = new HashSet<>();

    //a smaller list of words for selecting the target word from (i.e. the word the player needs to guess)
    public static ArrayList<String> targetWords = new ArrayList<>();

    public static void main(String[] args) {
        //load in the two word lists
        try{
            Scanner in_dict  = new Scanner(new File("gameDictionary.txt"));
            while(in_dict.hasNext()){
                dictionary.add(in_dict.next());
            }

            Scanner in_targets = new Scanner(new File("targetWords.txt"));
            while(in_targets.hasNext()){
                targetWords.add(in_targets.next());
            }
            in_dict.close();
            in_targets.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(Main::new);
    }

    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));
        //don't delete this line.
        System.out.println(target);
        return target;
    }

    public void actionPerformed(ActionEvent e) {

        String tmpUserInput = this.userInput.getText().toLowerCase();

        String[] seperatedLettersUserWord = tmpUserInput.split("");
        System.out.println(tmpUserInput);
        String[] seperatedLettersTargetWord2 = theTargetWord.split("");

        List<Boolean> booleanList = new ArrayList<>();

        int greenCount=0;
        int yellowCount=0;



        if(e.getActionCommand().equals("OK") && Main.dictionary.contains(tmpUserInput)){

            for(int r=0;r<theTargetWord.length();r++){

                if(theTargetWord.contains(seperatedLettersUserWord[r])){

                    if(seperatedLettersUserWord[r].equals(String.valueOf(seperatedLettersTargetWord2[r]))){
                        wordArray[tmpMatchLetterCount].wordInEachColum[r].setText(seperatedLettersUserWord[r]);
                        wordArray[tmpMatchLetterCount].wordInEachColum[r].setBackground(Color.GREEN);
                        wordArray[tmpMatchLetterCount].wordInEachColum[r].setBorder(BorderFactory.createLineBorder(Color.RED));
                        greenCount++;
                        booleanList.add(true);
                    }else{
                        wordArray[tmpMatchLetterCount].wordInEachColum[r].setText(seperatedLettersUserWord[r]);
                        wordArray[tmpMatchLetterCount].wordInEachColum[r].setBackground(Color.YELLOW);
                        yellowCount++;
                        booleanList.add(false);
                    }
                }else{
                    wordArray[tmpMatchLetterCount].wordInEachColum[r].setText(seperatedLettersUserWord[r]);
                    wordArray[tmpMatchLetterCount].wordInEachColum[r].setBackground(Color.GRAY);
                    booleanList.add(false);
                }
            }
            System.out.println("yellowCount: "+yellowCount);
            System.out.println("greenCount: "+greenCount);
            tmpMatchLetterCount++;
        }else{
            JOptionPane.showMessageDialog(null, "Your answer is not in the list!" + "You have "+(5-tmpMatchLetterCount)+" attempts left", "Your answer does not exist!", JOptionPane.INFORMATION_MESSAGE);
        }

        if(tmpUserInput.length()>4){
            if(greenCount==5){

              JOptionPane.showMessageDialog(null, "You found the right word!", "You won the game!", JOptionPane.INFORMATION_MESSAGE);
              this.dispose();
             }
            else if(yellowCount>0){
                JOptionPane.showMessageDialog(null, "You are nearly there, but you need to put the yellow character(s) in the right location"+ "You have "+(5-tmpMatchLetterCount)+" attempts left", "You are not quite finished", JOptionPane.INFORMATION_MESSAGE);
             }
            else{
                JOptionPane.showMessageDialog(null, "You did not find the right word", "The game continues!", JOptionPane.INFORMATION_MESSAGE);
             }
          }
        else{
            JOptionPane.showMessageDialog(null, "You did not complete your answer, the number of words should be 5 words and not less!" + "You have "+(5-tmpMatchLetterCount)+" attempts left", "You did not finish your answer!", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}






















