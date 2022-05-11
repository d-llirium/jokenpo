package server;

public class Validate {
    public int stringToInt( String msg) {
        int num = 0;
        if (msg.contains("1") || msg.contains("2") || msg.contains("3")){ 
            switch (msg) {
                case "1":
                num = 1;
                break;
                case "2":
                num = 2;
                break;
                case "3":
                num = 3;
                break;
                default:
                num = 0;
            }
        }
        return num;
    }
}
