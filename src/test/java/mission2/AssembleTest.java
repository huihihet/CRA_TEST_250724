package mission2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static mission2.Constant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssembleTest {

    Assemble assemble;

    @BeforeEach
    void setUp() {
        assemble = new Assemble();
    }

    @Test
    public void testMakeNormalCar() {
        String input = "1\n1\n3\n1\n1\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        assemble.makingCarLogic(scanner);

        assertEquals(CAR_TYPE.SEDAN, assemble.getCar().carType);
        assertEquals(ENGINE.GM, assemble.getCar().engine);
        assertEquals(BRAKE.BOSCH_B, assemble.getCar().brake);
        assertEquals(STEERING.BOSCH_S, assemble.getCar().steering);
    }

    @Test
    public void testInputInvalid() {
        String input = "\na\n4\n1\n5\n1\n4\n1\n3\n1\n3\n1\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("ERROR :: 숫자만 입력 가능"));
        assertTrue(consoleOutput.contains("ERROR :: 차량 타입은 1 ~ 3 범위만 선택 가능"));
        assertTrue(consoleOutput.contains("ERROR :: 엔진은 1 ~ 4 범위만 선택 가능"));
        assertTrue(consoleOutput.contains("ERROR :: 제동장치는 1 ~ 3 범위만 선택 가능"));
        assertTrue(consoleOutput.contains("ERROR :: 조향장치는 1 ~ 2 범위만 선택 가능"));
        assertTrue(consoleOutput.contains("ERROR :: Run 또는 Test 중 하나를 선택 필요"));
    }

    @Test
    public void testGoBack() {
        String input = "1\n0\n3\n1\n2\n2\n2\n1\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        assemble.makingCarLogic(scanner);

        assertEquals(CAR_TYPE.TRUCK, assemble.getCar().carType);
    }

    @Test
    public void testBrokenEngine() {
        String input = "1\n4\n1\n1\n1\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);
        String consoleOutput = outputStream.toString();

        assertTrue(consoleOutput.contains("엔진이 고장나있습니다."));
    }

    @Test
    public void testMakeNotFunctioningCarSedan2Continental() {
        String input = "1\n1\n2\n1\n1\n2\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("자동차가 동작되지 않습니다"));
        assertTrue(consoleOutput.contains("자동차 부품 조합 테스트 결과 : FAIL"));
        assertTrue(consoleOutput.contains("Sedan에는 Continental제동장치 사용 불가"));
    }

    @Test
    public void testMakeNotFunctioningCarSuv2Toyota() {
        String input = "2\n2\n2\n1\n1\n2\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("자동차가 동작되지 않습니다"));
        assertTrue(consoleOutput.contains("자동차 부품 조합 테스트 결과 : FAIL"));
        assertTrue(consoleOutput.contains("SUV에는 TOYOTA엔진 사용 불가"));
    }

    @Test
    public void testMakeNotFunctioningCarTruck2Wia() {
        String input = "3\n3\n2\n1\n1\n2\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("자동차가 동작되지 않습니다"));
        assertTrue(consoleOutput.contains("자동차 부품 조합 테스트 결과 : FAIL"));
        assertTrue(consoleOutput.contains("Truck에는 WIA엔진 사용 불가"));
    }

    @Test
    public void testMakeNotFunctioningCarTruck2Mando() {
        String input = "3\n1\n1\n1\n1\n2\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("자동차가 동작되지 않습니다"));
        assertTrue(consoleOutput.contains("자동차 부품 조합 테스트 결과 : FAIL"));
        assertTrue(consoleOutput.contains("Truck에는 Mando제동장치 사용 불가"));
    }

    @Test
    public void testMakeNotFunctioningCarBosch2Bosch() {
        String input = "1\n1\n3\n2\n1\n2\n0\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        assemble.makingCarLogic(scanner);

        System.setOut(System.out);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("자동차가 동작되지 않습니다"));
        assertTrue(consoleOutput.contains("자동차 부품 조합 테스트 결과 : FAIL"));
        assertTrue(consoleOutput.contains("Bosch제동장치에는 Bosch조향장치 이외 사용 불가"));
    }
}