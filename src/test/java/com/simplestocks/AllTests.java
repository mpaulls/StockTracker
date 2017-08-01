package com.simplestocks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommonStockTest.class, PreferredStockTest.class, StockTrackerTest.class, TradeTest.class })

public class AllTests {

}
