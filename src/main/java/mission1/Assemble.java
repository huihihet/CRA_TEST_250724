package mission1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assemble {
    private static final String CLEAR_SCREEN = "\033[H\033[2J";

    private static final int CarType_Q = 0;
    private static final int Engine_Q = 1;
    private static final int BrakeSystem_Q = 2;
    private static final int SteeringSystem_Q = 3;
    private static final int Run_Test = 4;

    private static final int SEDAN = 1, SUV = 2, TRUCK = 3;
    private static final int GM = 1, TOYOTA = 2, WIA = 3;
    private static final int MANDO = 1, CONTINENTAL = 2, BOSCH_B = 3;
    private static final int BOSCH_S = 1, MOBIS = 2;

    private static Map<Integer, String> carTable;
    private static Map<Integer, String> engineTable;
    private static Map<Integer, String> breakTable;
    private static Map<Integer, String> steeringTable;

    private static int[] selectFieldArr = new int[5];

    public static void main(String[] args) {
        makingCarLogic();
    }

    private static void makingCarLogic() {
        Scanner sc = new Scanner(System.in);
        int step = CarType_Q;
        initTable();

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

    private static void initTable() {
        carTable = new HashMap<>(Map.of(SEDAN, "SEDAN", SUV, "SUV", TRUCK, "TRUCK"));
        engineTable = new HashMap<>(Map.of(GM, "GM", TOYOTA, "TOYOTA", WIA, "WIA", 4, "고장난 엔진"));
        breakTable = new HashMap<>(Map.of(MANDO, "MANDO", CONTINENTAL, "CONTINENTAL", BOSCH_B, "BOSCH"));
        steeringTable = new HashMap<>(Map.of(BOSCH_S, "BOSCH", MOBIS, "MOBIS"));
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
        System.out.println("1. Sedan");
        System.out.println("2. SUV");
        System.out.println("3. Truck");
        System.out.println("===============================");
    }

    private static void showEngineMenu() {
        System.out.println("어떤 엔진을 탑재할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        System.out.println("1. GM");
        System.out.println("2. TOYOTA" +
                (selectFieldArr[CarType_Q] == SUV ? " (SUV는 TOYOTA 엔진 사용 불가)" : ""));
        System.out.println("3. WIA" +
                (selectFieldArr[CarType_Q] == TRUCK ? " (TRUCK은 WIA 엔진 사용 불가)" : ""));
        System.out.println("4. 고장난 엔진");
        System.out.println("===============================");
    }

    private static void showBrakeMenu() {
        System.out.println("어떤 제동장치를 선택할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        System.out.println("1. MANDO" +
                (selectFieldArr[CarType_Q] == TRUCK ? " (TRUCK은 MANDO 제동장치 사용 불가)" : ""));
        System.out.println("2. CONTINENTAL" +
                (selectFieldArr[CarType_Q] == SEDAN ? " (SEDAN은 CONTINENTAL 제동장치 사용 불가)" : ""));
        System.out.println("3. BOSCH");
        System.out.println("===============================");
    }

    private static void showSteeringMenu() {
        System.out.println("어떤 조향장치를 선택할까요? (시스템 종료 : exit)");
        System.out.println("0. 뒤로가기");
        System.out.println("1. BOSCH" +
                (selectFieldArr[BrakeSystem_Q] == BOSCH_B ? " (제동장치가 BOSCH이므로 BOSCH 사용 권장)" : ""));
        System.out.println("2. MOBIS");
        System.out.println("===============================");
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
        selectFieldArr[CarType_Q] = carType;
        System.out.printf("차량 타입으로 %s을 선택하셨습니다.\n", carTable.get(carType));
    }

    private static void selectEngine(int engine) {
        selectFieldArr[Engine_Q] = engine;
        System.out.printf("%s 엔진을 선택하셨습니다.\n", engineTable.get(engine));
    }

    private static void selectBrakeSystem(int breakSystem) {
        selectFieldArr[BrakeSystem_Q] = breakSystem;
        System.out.printf("%s 제동장치를 선택하셨습니다.\n", breakTable.get(breakSystem));
    }

    private static void selectSteeringSystem(int steeringSystem) {
        selectFieldArr[SteeringSystem_Q] = steeringSystem;
        System.out.printf("%s 조향장치를 선택하셨습니다.\n", steeringTable.get(steeringSystem));
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
        printCarInfo();
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
        return selectFieldArr[CarType_Q] == SEDAN && selectFieldArr[BrakeSystem_Q] == CONTINENTAL;
    }

    private static boolean isSuvUseToyotaEngine() {
        return selectFieldArr[CarType_Q] == SUV && selectFieldArr[Engine_Q] == TOYOTA;
    }

    private static boolean isTruckUseWiaEngine() {
        return selectFieldArr[CarType_Q] == TRUCK && selectFieldArr[Engine_Q] == WIA;
    }

    private static boolean isTruckUseMandoEngine() {
        return selectFieldArr[CarType_Q] == TRUCK && selectFieldArr[BrakeSystem_Q] == MANDO;
    }

    private static boolean isUseBoschBreakAndSteering() {
        return selectFieldArr[BrakeSystem_Q] == BOSCH_B && selectFieldArr[SteeringSystem_Q] != BOSCH_S;
    }

    private static boolean isBrokenEngine() {
        return selectFieldArr[Engine_Q] == 4;
    }

    private static void printCarInfo() {
        System.out.println("Car Type : " + carTable.get(selectFieldArr[CarType_Q]));
        System.out.println("Engine   : " + engineTable.get(selectFieldArr[Engine_Q]));
        System.out.println("Brake    : " + breakTable.get(selectFieldArr[BrakeSystem_Q]));
        System.out.println("Steering : " + steeringTable.get(selectFieldArr[SteeringSystem_Q]));
        System.out.println("자동차가 동작됩니다.");
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
}
