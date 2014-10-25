#ifndef READEREVENTLISTENER_H
#define READEREVENTLISTENER_H

struct ListenerImpl;

class ReaderEventListener
{
	public:
		ReaderEventListener();
		virtual ~ReaderEventListener();

        ListenerImpl* impl;
    
		void viewNeedsFresh();
		//void showError(int errorCode);
	protected:
	private:
};

#endif // READEREVENTLISTENER_H
