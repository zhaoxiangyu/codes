#import "core/port/ReaderEventListener.h"
#import "JlViewController.h"

struct ListenerImpl{
    JlViewController* viewContorller;
};

ReaderEventListener::ReaderEventListener()
{
	//ctor
}

ReaderEventListener::~ReaderEventListener()
{
	//dtor
}

void ReaderEventListener::viewNeedsFresh(){
    [impl->viewContorller refreshView];
}
