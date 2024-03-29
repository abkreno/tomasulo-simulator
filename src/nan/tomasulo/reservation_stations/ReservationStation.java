package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public abstract class ReservationStation {
	public static final int ISSUED = 0;
	public static final int EXECUTE = 1;
	public static final int WRITE_BACK = 2;
	public static final int COMMIT = 3;
	public static final int FINISHED = 4;

	private int vj, vk; // values of source operands
	private int qj, qk; // values to be written to source operands
	private int id, robEntry, dst;
	private int timer;
	private boolean busy = false;
	private String operation;
	private static int stationID = 1;
	private int executionTime;
	private int currStage;
	private short address;
	private short result;
	private Instruction instruction;

	public ReservationStation() {
		this.id = stationID++;
		this.vj = 0;
		this.vk = 0;
		this.qj = 0;
		this.qk = 0;
		this.busy = false;
		this.currStage = FINISHED;
		this.instruction = null;
	}

	public void reset() {
		this.vj = 0;
		this.vk = 0;
		this.qj = 0;
		this.qk = 0;
		this.busy = false;
		this.currStage = FINISHED;
		this.instruction = null;
	}

	public int getVj() {
		return vj;
	}

	public void setVj(int vj) {
		this.vj = vj;
	}

	public int getVk() {
		return vk;
	}

	public void setVk(int vk) {
		this.vk = vk;
	}

	public int getQj() {
		return qj;
	}

	public void setQj(int qj) {
		this.qj = qj;
	}

	public int getQk() {
		return qk;
	}

	public void setQk(int qk) {
		this.qk = qk;
	}

	public short getAddress() {
		return address;
	}

	public void setAddress(short address) {
		this.address = address;
	}

	public int getIndex() {
		return id;
	}

	public void setIndex(int index) {
		this.id = index;
	}

	public int getRobEntry() {
		return robEntry;
	}

	public void setRobEntry(int robEntry) {
		this.robEntry = robEntry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDst() {
		return dst;
	}

	public void setDst(int dst) {
		this.dst = dst;
	}

	public int getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}

	public int getCurrStage() {
		return currStage;
	}

	public void setCurrStage(int currStage) {
		this.currStage = currStage;
	}

	public short getResult() {
		return result;
	}

	public void setResult(short result) {
		this.result = result;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setTimer(int time) {
		this.timer = time;
	}

	public int getTimer() {
		return timer;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public short calculate(int vj, int vk, String operation) {
		switch (operation) {
		case "ADD":
		case "ADDI":
			return (short) (vj + vk);

		case "SUB":
			return (short) (vj - vk);

		case "MUL":
			return (short) (vj * vk);

		case "NAND":
			return (short) ~(vj & vk);

		default:
			break;
		}
		return 0;
	}

	public abstract void update();

	public abstract void reserve(Instruction instruction, int robEntry);
}
