#import "core/port/ReaderEventListener.h"
#import "JlViewController.h"

struct ListenerImpl{
    JlViewController* viewController;
};

ReaderEventListener::ReaderEventListener()
{
	//ctor
    impl = new ListenerImpl();
}

ReaderEventListener::~ReaderEventListener()
{
	//dtor
    delete impl;
}

void ReaderEventListener::viewNeedsFresh(){
    [impl->viewController refreshView];
}

//void ReaderEventListener::showError(int errorCode){
//    //no need implment
//}
