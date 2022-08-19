package src;

import java.util.Stack;

public class LineBrainFuck {
    private final String code;
    private final byte[] memory;
    private int currentIndex;
    private String userInput;

    private String result = "";
    private Stack<Integer> startLoopIndexesStack;

    public LineBrainFuck(String code) {
        this.code = code;
        this.memory = new byte[2000];
        this.currentIndex = 0;
        this.startLoopIndexesStack = new Stack<>();
    }

    public String process(String input) {
        this.userInput = input;
        for(int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case Commands.INCREMENT -> incrementThePointer();
                case Commands.DECREMENT -> decrementThePointer();
                case Commands.MOVE_LEFT -> moveLeft();
                case Commands.MOVE_RIGHT -> moveRight();
                case Commands.PRINT -> outputTheByte();
                case Commands.INPUT -> i = inputTheByte(i);
                case Commands.START_LOOP -> i = startLoop(i);
                case Commands.END_LOOP -> i = endLoop(i);
            }
        }
        return result;
    }

    private void incrementThePointer() {
        memory[currentIndex]++;
    }

    private void decrementThePointer() {
        memory[currentIndex]--;
    }

    private void moveRight() {
        currentIndex++;
    }

    private void moveLeft() {
        currentIndex--;
    }

    private void outputTheByte() {
        result += new String(new byte[]{ memory[currentIndex]});
    }

    private int inputTheByte(int codeIndex) {
        if(userInput.equals("")) {
            return code.length() + 1;
        }
        byte inputByte = userInput.getBytes()[0];
        memory[currentIndex] = inputByte;
        userInput = parseToString(removeFirstObject(userInput.getBytes()));
        return codeIndex;
    }

    private String parseToString(byte[] bytes) {
        return new String(bytes);
    }

    private byte[] removeFirstObject(byte[] array) {
        byte[] newArr = new byte[array.length - 1];
        for(int i = 0; i < newArr.length; i++) {
            newArr[i] = array[i + 1];
        }
        return newArr;
    }

    private int startLoop(int codeIndex) {
        startLoopIndexesStack.add(codeIndex);
        if(memory[currentIndex] == 0) {
            for(int i = codeIndex; i < code.length(); i++) {
                if(code.charAt(i) == Commands.END_LOOP) {
                    return i + 1;
                }
            }
        }
        return codeIndex;
    }

    private int endLoop(int codeIndex) {
        if(memory[currentIndex] != 0) {
            return startLoopIndexesStack.lastElement();
        }
        startLoopIndexesStack.pop();
        return codeIndex;
    }
}
