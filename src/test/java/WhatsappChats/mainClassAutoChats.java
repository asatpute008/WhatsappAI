package WhatsappChats;

import Project.WhatsappPanel;

public class mainClassAutoChats {


	@SuppressWarnings("unused")
	public void ExecuteWhatsappChats() throws InterruptedException {

		WhatsappPanel whatsapp = new  WhatsappPanel();
		whatsapp.openWhatsapp();

		Outerloop:do {
			whatsapp.unReadChatList();
			System.out.println("Unread Chat Open -");

			unreadchatloop:do {
				boolean presentChatMessageAvailavble = false;
				boolean unReadChatAvailibility = whatsapp.AvailableChats();
				int newMessageValue = 0;

				if(presentChatMessageAvailavble == true) {
					System.out.println("Current Chat Message Available");
					
				}else if(unReadChatAvailibility  == true) {
					whatsapp.OpenChat();
					
					newMessageLoop:do {
						newMessageValue++;
						System.out.println("New Message Available - "+ newMessageValue);
						Thread.sleep(1000);
					}while(newMessageValue < 10);
					break unreadchatloop;
					
				}else {
					System.out.println("Unread Chats Not Available");
					break unreadchatloop;
					
				}

			}while(true);

		}while(true);
	}

}
