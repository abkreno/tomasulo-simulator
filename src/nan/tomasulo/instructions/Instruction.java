package nan.tomasulo.instructions;

import nan.tomasulo.Parser;

public class Instruction {
	private String type;
	private String instructionFormat;
	private int rd, rs, rt, imm, address;
	private int issuedTime, executedTime, writtenTime, commitedTime;
	private boolean issued;

	public Instruction() {
		this.issued = true;
	}

	public Instruction(String instruction, int address) {
		this.instructionFormat = instruction;
		this.address = address;
		String[] split = instruction.split(" ");
		this.type = split[0];
		this.issued = false;
		int[] vals = new int[3];
		String[] split2 = split[1].split(",");
		for (int i = 0; i < split2.length; i++) {
			vals[i] = Parser.parseRegisters(split2[i]);
		}
		initializeRegistersValues(vals[0], vals[1], vals[2]);
	}

	public void initializeRegistersValues(int a, int b, int c) {
		if (Parser.checkTypeCondBranch(type) || Parser.checkTypeLoadStore(type)) {
			if (type.equals("LW")) {
				rd = a;
				rs = b;
				imm = c;
			} else {
				rs = a;
				rt = b;
				imm = c;
			}
		} else if (Parser.checkTypeCall(type)) {
			rd = a;
			rs = b;
		} else if (Parser.checkTypeUncondBranch(type)) {
			rd = a;
			imm = b;
		} else if (Parser.checkTypeRet(type)) {
			rd = a;
		} else if (Parser.checkTypeArithmetic(type)) {
			rd = a;
			rs = b;
			rt = c;
		} else if (Parser.checkTypeImmArithmetic(type)) {
			rd = a;
			rs = b;
			imm = c;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getRs() {
		return rs;
	}

	public void setRs(int rs) {
		this.rs = rs;
	}

	public int getRt() {
		return rt;
	}

	public void setRt(int rt) {
		this.rt = rt;
	}

	public int getImmediate() {
		return imm;
	}

	public void setImmediate(int imm) {
		this.imm = imm;
	}

	public int getImm() {
		return imm;
	}

	public void setImm(int imm) {
		this.imm = imm;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public boolean isIssued() {
		return issued;
	}

	public void setIssued(boolean issued) {
		this.issued = issued;
	}

	public int getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(int issuedTime) {
		this.issuedTime = issuedTime;
	}

	public int getExecutedTime() {
		return executedTime;
	}

	public void setExecutedTime(int executedTime) {
		this.executedTime = executedTime;
	}

	public int getWrittenTime() {
		return writtenTime;
	}

	public void setWrittenTime(int writtenTime) {
		this.writtenTime = writtenTime;
	}

	public int getCommitedTime() {
		return commitedTime;
	}

	public void setCommitedTime(int commitedTime) {
		this.commitedTime = commitedTime;
	}

	public String toString() {
		return instructionFormat;
	}

	public String getLog() {
		StringBuffer log = new StringBuffer("Instruction " + toString() + "\t");
		log.append("Issued At " + issuedTime + "\t");
		log.append("Executed At " + executedTime + "\t");
		log.append("Written Back At " + writtenTime + "\t");
		log.append("Commited At " + commitedTime);
		return log.toString();
	}
}
