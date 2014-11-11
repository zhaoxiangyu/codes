//
//  JlViewController.m
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import "JlViewController.h"
#import "core/JpwordReader.h"

struct Impl {
    JpwordReader* r;
};

@implementation JlViewController{
//    JpwordReader* reader;
}

//@synthesize wordDisplay;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    UISwipeGestureRecognizer *swipeLeft = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(wordSwiped:)];
    [swipeLeft setDirection:UISwipeGestureRecognizerDirectionLeft];
    [wordDisplayView addGestureRecognizer:swipeLeft];
    UISwipeGestureRecognizer *swipeRight = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(wordSwiped:)];
    [swipeRight setDirection:UISwipeGestureRecognizerDirectionRight];
    [wordDisplayView addGestureRecognizer:swipeRight];

    UISwipeGestureRecognizer *swipeUp = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(wordSwiped:)];
    [swipeUp setDirection:UISwipeGestureRecognizerDirectionUp];
    [wordDisplayView addGestureRecognizer:swipeUp];
    UISwipeGestureRecognizer *swipeDown = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(wordSwiped:)];
    [swipeDown setDirection:UISwipeGestureRecognizerDirectionDown];
    [wordDisplayView addGestureRecognizer:swipeDown];
    
    NSLog(@"viewDidLoad finished");
    self.impl->r->start();
    //[app chooseCourse:1];
}

- (void)wordSwiped:(UISwipeGestureRecognizer *)gesture
{
    if (gesture.direction == UISwipeGestureRecognizerDirectionLeft)
    {
        [self impl]->r->back();
    }
    else if (gesture.direction == UISwipeGestureRecognizerDirectionRight)
    {
        [self impl]->r->forward();
    }
    else if (gesture.direction == UISwipeGestureRecognizerDirectionUp)
    {
        [self impl]->r->upLevel();
    }
    else if (gesture.direction == UISwipeGestureRecognizerDirectionDown)
    {
        [self impl]->r->downLevel();
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)refreshView
{
    string text = [self impl]->r->text();
    NSString* nsText = [NSString stringWithUTF8String: text.c_str()];
    NSLog(@"about to refresh UIx:%@",nsText);

    //NSLog(@"wordDisplay:%@",wordDisplay);
    [wordDisplay setText: nsText];
}

- (IBAction)toFirst:(id)sender
{
    
}

- (IBAction)doBtnAbout:(id)sender {
    //[sender setTitle: @"HA"];
    //[[self wordDisplay] setHidden:YES];
    [self refreshView];
}
@end