import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        printStartGame();

        do {
            int player = choiceNumberOfPlayers();

            if (player < 1 || player > 2) {
                System.out.println("Invalid input.");

            } else if (player == 1) {
                choiceNumberOfCity();
                String selectedCity = getNameCity()[choiceNumberOfCity()];
                String[] selectedCityArray = extractLetter(selectedCity);
                printEmptyPlace(selectedCityArray);
                String[] guessedCity = printLetterOfCityPlace(selectedCityArray);
                playGameOnePlayer(guessedCity, selectedCityArray);
                System.out.println();

            } else {
                String[] nameOfPlayers = new String[2];
                System.out.println("Enter name of first player: ");
                nameOfPlayers[0] = scanner.nextLine();
                System.out.println("Enter name of second player: ");
                nameOfPlayers[1] = scanner.nextLine();

                do {
                    choiceNumberOfCity();
                    String selectedCity = getNameCity()[choiceNumberOfCity()];
                    String[] selectedCityArray = extractLetter(selectedCity);
                    String[] guessedCity = printLetterOfCityPlace(selectedCityArray);
                    printEmptyPlace(selectedCityArray);
                    System.out.println();

                    do {
                        playGameTwoPlayer(guessedCity, selectedCityArray, nameOfPlayers[0]);
                        if (Arrays.equals(guessedCity, selectedCityArray)) {
                            break;
                        }
                        playGameTwoPlayer(guessedCity, selectedCityArray, nameOfPlayers[1]);

                    } while (!Arrays.equals(guessedCity, selectedCityArray));
                } while (printQuestionAboutANewGame());
                break;
            }
        }
        while (printQuestionAboutANewGame());
    }

    private static void printStartGame() {
        System.out.println("Hangman.");
        System.out.println("The game to work with a dictionary made up of the names of the settlements in Bulgaria.");
        System.out.println("Single player game - the game ends with 3 wrong attempts!");
        System.out.println("Game with two players - the game ends when someone guesses the word.");
        System.out.println();
    }

    public static int choiceNumberOfPlayers() {
        System.out.println("Select one or two players: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextByte();
    }

    private static int choiceNumberOfCity() {
        Random rand = new Random();
        return rand.nextInt(44);
    }

    public static String[] getNameCity() {
        return new String[]{
                "SOFIA", "BURGAS", "RUSE", "SLIVEN", "DOBRICH", "SHUMEN", "PERNIK", "YAMBOL", "ASENOVGRAD",
                "KYUSTENDIL", "TARGOVISHTE", "LOVECH", "DUPNITSA", "SMOLYAN", "PETRICH", "LOM", "VELINGRAD",
                "TROYAN", "AYTOS", "BOTEVGRAD", "SVILENGRAD", "CHIRPAN", "RADOMIR", "BERKOVITSA", "RADNEVO",
                "RAZLOG", "BALCHIK", "KNEZHA", "MEZDRA", "LEVSKI", "LUKOVIT", "ISPERIH", "OMURTAG", "BANSKO",
                "DEVNYA", "LYASKOVETS", "SVOGE", "KUBRAT", "LYUBIMETS", "DEVIN", "KOTEL", "TSAREVO", "BOZHURISHTE",
                "BELOGRADCHIK", "KRUMOVGRAD"
        };
    }

    public static String[] extractLetter(String selectedCity) {
        String[] selectedCityArray = new String[selectedCity.length()];

        for (int i = 0; i < selectedCity.length(); i++) {
            selectedCityArray[i] = String.valueOf(selectedCity.charAt(i));
        }
        return selectedCityArray;
    }

    public static void printEmptyPlace(String[] selectedCity) {
        String[] guessedCity = new String[selectedCity.length];
        for (int i = 0; i < selectedCity.length; i++) {
            guessedCity[i] = "_";
            System.out.print(guessedCity[i]);
        }
    }

    public static String[] printLetterOfCityPlace(String[] selectedCity) {
        String[] guessedCity = new String[selectedCity.length];
        for (int i = 0; i < selectedCity.length; i++) {
            guessedCity[i] = "_";
        }
        return guessedCity;
    }

    public static void playGameOnePlayer(String[] guessedCity, String[] selectedCityArray) {
        Scanner scanner = new Scanner(System.in);
        String enterLetter;
        int wrongAttempts = 0;

        do {
            System.out.println();
            System.out.print("Enter letter: ");
            enterLetter = scanner.nextLine();
            String enterLetterUpperCase = enterLetter.toUpperCase(Locale.ROOT);
            wrongAttempts += 1;

            for (int i = 0; i < selectedCityArray.length; i++) {

                if (enterLetterUpperCase.equals(selectedCityArray[i])) {
                    wrongAttempts -= 1;
                    guessedCity[i] = selectedCityArray[i];
                } else {
                    guessedCity[i] = guessedCity[i];
                }
                System.out.print(guessedCity[i]);

                if (Arrays.equals(guessedCity, selectedCityArray)) {
                    System.out.println();
                    System.out.println("You win!");
                    return;
                }
            }
            System.out.println();
            printHanged(wrongAttempts);

            System.out.println();
        } while (wrongAttempts <= 2);
        System.out.println("Game over!");
    }

    public static void playGameTwoPlayer(String[] guessedCity, String[] selectedCityArray, String namePlayers) {
        Scanner scanner = new Scanner(System.in);

        String enterLetter;
        int pointsPlayer = 0;
        int wrongAttemptsPlayer = 1;

        do {
            System.out.print("Enter letter " + namePlayers + ":");
            enterLetter = scanner.nextLine();
            String enterLetterUpperCase = enterLetter.toUpperCase(Locale.ROOT);
            wrongAttemptsPlayer += 1;

            for (int i = 0; i < selectedCityArray.length; i++) {

                if (enterLetterUpperCase.equals(selectedCityArray[i])) {
                    wrongAttemptsPlayer -= 1;

                    guessedCity[i] = selectedCityArray[i];
                } else {
                    guessedCity[i] = guessedCity[i];
                }
                System.out.print(guessedCity[i]);

                if (Arrays.equals(guessedCity, selectedCityArray)) {
                    System.out.println();
                    System.out.println(namePlayers + " win!");
                    pointsPlayer += 1;
                    System.out.println(namePlayers + "'s Points: " + pointsPlayer);
                    return;
                }
            }
            System.out.println();
        } while (wrongAttemptsPlayer <= 1);
        System.out.println("Wrong Attempt.");
        System.out.println();
    }

    public static void printHanged(int wrongAttempts) {
        if (wrongAttempts == 1) {
            System.out.println();
            for (int i = 0; i < 5; i++) {
                System.out.println("|");
            }
        } else if (wrongAttempts == 2) {
            System.out.println();
            for (int i = 0; i < 7; i++) {
                System.out.print("-");
            }
            System.out.println();
            for (int j = 0; j < 5; j++) {
                System.out.println("|");
            }
        } else if (wrongAttempts == 3) {
            System.out.println();
            for (int i = 0; i < 7; i++) {
                System.out.print("-");
            }
            System.out.println();
            System.out.println("|     o");
            System.out.println("|    /|\\ ");
            System.out.println("|     |");
            System.out.println("|    / \\");
            System.out.println("|");
            System.out.println();

        } else {
            System.out.println();
        }
    }

    private static boolean printQuestionAboutANewGame() throws IOException {
        System.out.println("Start new game? Y/N");
        char readNewGame = (char) System.in.read();
        new Scanner(System.in).nextLine();
        return readNewGame == 89 || readNewGame == 121;
    }
}
