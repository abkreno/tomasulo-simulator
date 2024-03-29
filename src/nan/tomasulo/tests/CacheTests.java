package nan.tomasulo.tests;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Cache;
import nan.tomasulo.cache.CacheBlock;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;

import org.junit.Test;

public class CacheTests {
	@Test
	public void testReadDataFromCacheBlock() throws InvalidWriteException,
			InvalidReadException {
		int[][] cachesInfo = new int[2][]; // two caches
		// 512 words , 16 word per block , direct map , write through
		cachesInfo[0] = new int[] { 512, 16, 1, 1, 0 }; // 32 blocks
		// 1024 words , 16 word per block , direct map , write through
		cachesInfo[1] = new int[] { 1024, 16, 1, 1, 0 }; // 64 blocks
		Caches.initCaches(cachesInfo);
		LinkedList<Cache> dataCaches = Caches.getDataCaches();
		Cache cacheL1 = dataCaches.get(0);
		Cache cacheL2 = dataCaches.get(1);
		Memory.init(16);
		Memory.writeDataEntry(0, new Short((short) 9771));
		Memory.writeDataEntry(15, new Short((short) 9799));
		CacheBlock block = Caches.readCacheBlock((short) 0, 0, dataCaches);
		assertTrue("Entry 0 should contain 9771", block.getEntry((short) 0)
				.getData().equals(new Short((short) 9771)));
		assertTrue("Entry 15 should contain 9799", block.getEntry((short) 15)
				.getData().equals(new Short((short) 9799)));
		assertTrue("Number of hits of Cache Level 1 should be 1",
				cacheL1.getHits() == 1);
		assertTrue("Number of misses of Cache Level 1 should be 1",
				cacheL1.getMisses() == 1);
		assertTrue("Number of hits of Cache Level 2 should be 1",
				cacheL2.getHits() == 1);
		assertTrue("Number of misses of Cache Level 2 should be 1",
				cacheL2.getMisses() == 1);

		CacheBlock block2 = Caches.readCacheBlock((short) 0, 0, dataCaches);

		assertTrue("Entry 0 should contain 9771", block2.getEntry((short) 0)
				.getData().equals(new Short((short) 9771)));
		assertTrue("Entry 15 should contain 9799", block2.getEntry((short) 15)
				.getData().equals(new Short((short) 9799)));
		assertTrue("Number of hits of Cache Level 1 should be 2",
				cacheL1.getHits() == 2);
		assertTrue("Number of misses of Cache Level 1 should be 1",
				cacheL1.getMisses() == 1);
		assertTrue("Number of hits of Cache Level 2 should be 1",
				cacheL2.getHits() == 1);
		assertTrue("Number of misses of Cache Level 2 should be 1",
				cacheL2.getMisses() == 1);

	}

	@Test
	public void testReadInstructionFromCacheBlock()
			throws InvalidWriteException, InvalidReadException {
		int[][] cachesInfo = new int[1][]; // two caches
		// 512 words , 16 word per block , direct map , write through
		cachesInfo[0] = new int[] { 512, 16, 1, 1, 0 }; // 32 blocks
		Caches.initCaches(cachesInfo);
		Parser.copyProgramToMemory("program_1.in");
		String instruction = Caches.fetchInstruction((short) 0);
		assertTrue("first instruction is ADD R1,R2,R3",
				instruction.equals("ADD R1,R2,R3"));
	}

	@Test
	public void testWriteThrough() throws InvalidWriteException,
			InvalidReadException {
		int[][] cachesInfo = new int[1][]; // two caches
		// 512 words , 16 word per block , direct map , write through
		cachesInfo[0] = new int[] { 512, 16, 1, 1, 0 }; // 32 blocks
		Caches.initCaches(cachesInfo);
		Memory.init(16);
		Processor p = new Processor(1, 4);

		// 0 at block (0/16 = 0 % 32 = 0)
		p.writeData((short) 0, new Short((short) 75));
		// 2048 at block ( 2048/16 = 128 % 32 = 0)
		p.writeData((short) 2048, new Short((short) 105));
		Short data1 = Caches.fetchData((short) 0);
		Short data2 = Caches.fetchData((short) 2048);
		assertTrue("data1 should be 75 , data2 should be 105",
				data1.equals((short) 75) && data2.equals((short) 105));
	}

	@Test
	public void testWriteBack() throws InvalidWriteException,
			InvalidReadException {
		int[][] cachesInfo = new int[1][]; // two caches
		// 512 words , 16 word per block , direct map , write back
		cachesInfo[0] = new int[] { 512, 16, 1, 1, 1 }; // 32 blocks
		Caches.initCaches(cachesInfo);
		Memory.init(16);
		Processor p = new Processor(1, 4);

		// 0 at block (0/16 = 0 % 32 = 0)
		p.writeData((short) 0, new Short((short) 75));
		// 2048 at block ( 2048/16 = 128 % 32 = 0)
		p.writeData((short) 2048, new Short((short) 105));
		Short data1 = Caches.fetchData((short) 0);
		Short data2 = Caches.fetchData((short) 2048);
		assertTrue("data1 should be 75 , data2 should be 105",
				data1.equals((short) 75) && data2.equals((short) 105));
	}
}
