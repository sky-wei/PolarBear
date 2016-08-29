package com.wei.polarbear.tools;

import org.junit.Assert;
import org.junit.Test;

public class ToolsTest {


	@Test
	public void testConcealBack() {
		Assert.assertEquals("jing@@@@@@@", Tools.concealBack("jingcai.wei", 4));
		Assert.assertEquals("jing@", Tools.concealBack("jingc", 4));
		Assert.assertEquals("jing", Tools.concealBack("jing", 4));
		Assert.assertEquals("jin", Tools.concealBack("jin", 4));
		Assert.assertEquals(null, Tools.concealBack("", 4));
	}
	
	@Test
	public void testConcealFirst() {
		
		Assert.assertEquals("@@@@cai.wei", Tools.concealFirst("jingcai.wei", 4));
		Assert.assertEquals("@@@@c", Tools.concealFirst("jingc", 4));
		Assert.assertEquals("@@@@", Tools.concealFirst("jing", 4));
		Assert.assertEquals("@@@", Tools.concealFirst("jin", 4));
		Assert.assertEquals(null, Tools.concealFirst("", 4));
	}
}
