package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public class AddUnit extends ReservationStation {

	public AddUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reserve(Instruction instruction) {
		// TODO Auto-generated method stub

	}

}