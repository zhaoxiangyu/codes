//
//  JlViewController.m
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import "JlViewController.h"
#import "JlAppDelegate.h"

@implementation JlViewController{
    JlAppDelegate* app;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    //[app chooseCourse:1];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)toFirst:(id)sender
{
    [app toFirst];
}

@end