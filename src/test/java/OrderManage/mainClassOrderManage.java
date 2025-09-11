package OrderManage;

public class mainClassOrderManage {

	public void ExecuteOrdermManage() throws InterruptedException {
        int value = 1;
		do {
			System.out.println("Execute order management");
			value++;
			Thread.sleep(2000);
		}while(value < 11);
	}
}
