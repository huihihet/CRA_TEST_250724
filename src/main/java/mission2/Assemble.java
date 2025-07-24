package mission2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static mission2.Constant.*;
import static mission2.Constant.CAR_TYPE.*;
import static mission2.Constant.ENGINE.*;
import static mission2.Constant.BRAKE.*;
import static mission2.Constant.STEERING.*;

public class Assemble {
    private static final String CLEAR_SCREEN = "\033[H\033[2J";

    private static final int CarType_Q = 0;
    private static final int Engine_Q = 1;
    private static final int BrakeSystem_Q = 2;
    private static final int SteeringSystem_Q = 3;
    private static final int Run_Test = 4;

    private static Car car;

//    public static void main(String[] args) {
//        Assemble assemble = new Assemble();
//        assemble.makingCarLogic(new Scanner(System.in));
//    }

    public void makingCarLogic(Scanner sc) {
        int step = CarType_Q;

        while (true) {
            System.out.print(CLEAR_SCREEN);
            System.out.flush();

            printDisplayView(step);

            String output = sc.nextLine().trim();

            if (isExit(output)) {
                System.out.println("바이바이");
                break;
            }

            Integer userCommand = parsingInteger(output);
            if (userCommand == null) continue;

            if (!isOutputValidRange(step, userCommand)) {
                returnValidErrorMessage(step);
                delay(800);
                continue;
            }

            if (isGoBack(userCommand)) {
                if (isGoFirstStep(step)) step = CarType_Q;
                else if (isGoBackStep(step)) step--;
                continue;
            }

            step = executeCommand(step, userCommand);
            System.out.println();
        }

        sc.close();
    }

    private static void printDisplayView(int step) {
        switch (step) {
            case CarType_Q:
                showCarTypeMenu();
                break;
            case Engine_Q:
                showEngineMenu();
                break;
            case BrakeSystem_Q:
                showBrakeMenu();
                break;
            case SteeringSystem_Q:
                showSteeringMenu();
                break;
            case Run_Test:
                showRunTestMenu();
                break;
        }
        System.out.print("INPUT > ");
    }

    private static void showCarTypeMenu() {
        System.out.println("        ______________");
        System.out.println("       /|            |");
        System.out.println("  ____/_|_____________|____");
        System.out.println(" |                      O  |");
        System.out.println(" '-(@)----------------(@)--'");
        System.out.println("===============================");
        System.out.println("어떤 차량 타입을 선택할까요? (시스템 종료 : exit)");
        carTable.entrySet().stream().map(entry ->
                entry.getKey() + ". " + entry.getValue()).forEach(System.out::println);
        System.out.println("===============================");
    }

    private static void showEngineMenu() {
        System.out.println("어떤 엔진을 탑재할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        for (Map.Entry<Integer, ENGINE> entry : engineTable.entrySet()) {
            System.out.print(entry.getKey() + ". " + (entry.getValue() == BROKEN ? "고장난 엔진" : entry.getValue()));
            if (isSuv2Toyota(entry.getValue())) System.out.print(" (SUV는 TOYOTA 엔진 사용 불가)");
            if (isTruck2Wia(entry.getValue())) System.out.print(" (TRUCK은 WIA 엔진 사용 불가)");
            System.out.println();
        }
        System.out.println("===============================");
    }

    private static boolean isSuv2Toyota(ENGINE value) {
        return car.carType == SUV && value == TOYOTA;
    }

    private static boolean isTruck2Wia(ENGINE value) {
        return car.carType == TRUCK && value == WIA;
    }

    private static void showBrakeMenu() {
        System.out.println("어떤 제동장치를 선택할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        for (Map.Entry<Integer, BRAKE> entry : brakeTable.entrySet()) {
            System.out.print(entry.getKey() + ". " + (entry.getValue() == BOSCH_B ? "BOSCH" : entry.getValue()));
            if (isTruck2Mando(entry.getValue())) System.out.print(" (TRUCK은 MANDO 제동장치 사용 불가)");
            if (isSedan2Continental(entry.getValue())) System.out.print(" (SEDAN은 CONTINENTAL 제동장치 사용 불가)");
            System.out.println();
        }
        System.out.println("===============================");
    }

    private static boolean isTruck2Mando(BRAKE value) {
        return car.carType == TRUCK && value == MANDO;
    }

    private static boolean isSedan2Continental(BRAKE value) {
        return car.carType == SEDAN && value == CONTINENTAL;
    }

    private static void showSteeringMenu() {
        System.out.println("어떤 조향장치를 선택할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        for (Map.Entry<Integer, STEERING> entry : steeringTable.entrySet()) {
            System.out.print(entry.getKey() + ". " + (entry.getValue() == BOSCH_S ? "BOSCH" : entry.getValue()));
            if (isBosch2Bosch(entry.getValue())) System.out.print(" (제동장치가 BOSCH이므로 BOSCH 사용 권장)");
            System.out.println();
        }
        System.out.println("===============================");
    }

    private static boolean isBosch2Bosch(STEERING value) {
        return car.brake == BOSCH_B && value == BOSCH_S;
    }

    private static void showRunTestMenu() {
        System.out.println("멋진 차량이 완성되었습니다.");
        System.out.println("어떤 동작을 할까요? (시스템 종료 : exit)");
        System.out.println("0. 처음 화면으로 돌아가기");
        System.out.println("1. Run");
        System.out.println("2. Test");
        System.out.println("===============================");
    }

    private static boolean isExit(String buf) {
        return buf.equalsIgnoreCase("exit");
    }

    private static Integer parsingInteger(String buf) {
        int answer;
        try {
            answer = Integer.parseInt(buf);
        } catch (NumberFormatException e) {
            System.out.println("ERROR :: 숫자만 입력 가능");
            delay(800);
            return null;
        }
        return answer;
    }

    private static boolean isOutputValidRange(int step, int ans) {
        switch (step) {
            case CarType_Q:
                if (ans < 1 || ans > 3) return false;
                break;
            case Engine_Q:
                if (ans < 0 || ans > 4) return false;
                break;
            case BrakeSystem_Q:
                if (ans < 0 || ans > 3) return false;
                break;
            case SteeringSystem_Q, Run_Test:
                if (ans < 0 || ans > 2) return false;
                break;
        }
        return true;
    }

    private static void returnValidErrorMessage(int step) {
        switch (step) {
            case CarType_Q:
                System.out.println("ERROR :: 차량 타입은 1 ~ 3 범위만 선택 가능");
                break;
            case Engine_Q:
                System.out.println("ERROR :: 엔진은 1 ~ 4 범위만 선택 가능");
                break;
            case BrakeSystem_Q:
                System.out.println("ERROR :: 제동장치는 1 ~ 3 범위만 선택 가능");
                break;
            case SteeringSystem_Q:
                System.out.println("ERROR :: 조향장치는 1 ~ 2 범위만 선택 가능");
                break;
            case Run_Test:
                System.out.println("ERROR :: Run 또는 Test 중 하나를 선택 필요");
                break;
        }
    }

    private static boolean isGoBack(Integer userCommand) {
        return userCommand == 0;
    }

    private static boolean isGoBackStep(int step) {
        return step > CarType_Q;
    }

    private static boolean isGoFirstStep(int step) {
        return step == Run_Test;
    }

    private static int executeCommand(int step, Integer userCommand) {
        switch (step) {
            case CarType_Q:
                selectCarType(userCommand);
                delay(800);
                step = Engine_Q;
                break;
            case Engine_Q:
                selectEngine(userCommand);
                delay(800);
                step = BrakeSystem_Q;
                break;
            case BrakeSystem_Q:
                selectBrakeSystem(userCommand);
                delay(800);
                step = SteeringSystem_Q;
                break;
            case SteeringSystem_Q:
                selectSteeringSystem(userCommand);
                delay(800);
                step = Run_Test;
                break;
            case Run_Test:
                if (isRun(userCommand)) {
                    runProducedCar();
                    delay(2000);
                } else if (isTest(userCommand)) {
                    System.out.println("Test...");
                    delay(1500);
                    testProducedCar();
                    delay(2000);
                }
                break;
        }
        return step;
    }

    private static void selectCarType(int carType) {
        CarFactory factory = new CarFactory();
        car = factory.makeCar(carTable.get(carType));
        System.out.printf("차량 타입으로 %s을 선택하셨습니다.\n", car.carType);
    }

    private static void selectEngine(int engine) {
        car.setEngine(engineTable.get(engine));
        System.out.printf("%s 엔진을 선택하셨습니다.\n", car.engine);
    }

    private static void selectBrakeSystem(int breakSystem) {
        car.setBrake(brakeTable.get(breakSystem));
        System.out.printf("%s 제동장치를 선택하셨습니다.\n", car.brake);
    }

    private static void selectSteeringSystem(int steeringSystem) {
        car.setSteering(steeringTable.get(steeringSystem));
        System.out.printf("%s 조향장치를 선택하셨습니다.\n", car.steering);
    }

    private static boolean isRun(Integer userCommand) {
        return userCommand == 1;
    }

    private static void runProducedCar() {
        if (!isValidCheck()) {
            System.out.println("자동차가 동작되지 않습니다");
            return;
        }
        if (isBrokenEngine()) {
            System.out.println("엔진이 고장나있습니다.");
            System.out.println("자동차가 움직이지 않습니다.");
            return;
        }

        car.printCarInfo();
    }

    private static boolean isBrokenEngine() {
        return car.engine == BROKEN;
    }

    private static boolean isValidCheck() {
        if (isSedanUseContinentalBreak()) return false;
        if (isSuvUseToyotaEngine()) return false;
        if (isTruckUseWiaEngine()) return false;
        if (isTruckUseMandoEngine()) return false;
        if (isUseBoschBreakAndSteering()) return false;
        return true;
    }

    private static boolean isSedanUseContinentalBreak() {
        return car.carType == SEDAN && car.brake == CONTINENTAL;
    }

    private static boolean isSuvUseToyotaEngine() {
        return car.carType == SUV && car.engine == TOYOTA;
    }

    private static boolean isTruckUseWiaEngine() {
        return car.carType == TRUCK && car.engine == WIA;
    }

    private static boolean isTruckUseMandoEngine() {
        return car.carType == TRUCK && car.brake == MANDO;
    }

    private static boolean isUseBoschBreakAndSteering() {
        return car.brake == BOSCH_B && car.steering != BOSCH_S;
    }

    private static boolean isTest(Integer userCommand) {
        return userCommand == 2;
    }

    private static void testProducedCar() {
        if (isSedanUseContinentalBreak()) fail("Sedan에는 Continental제동장치 사용 불가");
        else if (isSuvUseToyotaEngine()) fail("SUV에는 TOYOTA엔진 사용 불가");
        else if (isTruckUseWiaEngine()) fail("Truck에는 WIA엔진 사용 불가");
        else if (isTruckUseMandoEngine()) fail("Truck에는 Mando제동장치 사용 불가");
        else if (isUseBoschBreakAndSteering()) fail("Bosch제동장치에는 Bosch조향장치 이외 사용 불가");
        else System.out.println("자동차 부품 조합 테스트 결과 : PASS");
    }

    private static void fail(String msg) {
        System.out.println("자동차 부품 조합 테스트 결과 : FAIL");
        System.out.println(msg);
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    public Car getCar() {
        return car;
    }
}

class Constant {
    enum CAR_TYPE {
        SEDAN, SUV, TRUCK
    }

    enum ENGINE {
        GM, TOYOTA, WIA, BROKEN
    }

    enum BRAKE {
        MANDO, CONTINENTAL, BOSCH_B
    }

    enum STEERING {
        BOSCH_S, MOBIS
    }

    public static Map<Integer, CAR_TYPE> carTable = new HashMap<>(Map.of(1, SEDAN, 2, SUV, 3, TRUCK));
    public static Map<Integer, ENGINE> engineTable = new HashMap<>(Map.of(1, GM, 2, TOYOTA, 3, WIA, 4, BROKEN));
    public static Map<Integer, BRAKE> brakeTable = new HashMap<>(Map.of(1, MANDO, 2, CONTINENTAL, 3, BOSCH_B));
    public static Map<Integer, STEERING> steeringTable = new HashMap<>(Map.of(1, BOSCH_S, 2, MOBIS));
}

class Car {
    CAR_TYPE carType;
    ENGINE engine;
    BRAKE brake;
    STEERING steering;

    public void setEngine(ENGINE engine) {
        this.engine = engine;
    }

    public void setBrake(BRAKE brake) {
        this.brake = brake;
    }

    public void setSteering(STEERING steering) {
        this.steering = steering;
    }

    public void printCarInfo() {
        System.out.println("Car Type : " + this.carType);
        System.out.println("Engine   : " + this.engine);
        System.out.println("Brake    : " + (this.brake == BOSCH_B ? "BOSCH" : this.brake));
        System.out.println("Steering : " + (this.steering == BOSCH_S ? "BOSCH" : this.steering));
        System.out.println("자동차가 동작됩니다.");
    }
}

class CarFactory {
    public Car makeCar(CAR_TYPE carType) {
        return switch (carType) {
            case SEDAN -> new Sedan();
            case SUV -> new Suv();
            case TRUCK -> new Truck();
        };
    }
}

class Sedan extends Car {
    Sedan() {
        this.carType = SEDAN;
    }
}

class Suv extends Car {
    Suv() {
        this.carType = SUV;
    }
}

class Truck extends Car {
    Truck() {
        this.carType = TRUCK;
    }
}