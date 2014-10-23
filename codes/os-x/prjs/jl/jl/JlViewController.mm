//
//  JlViewController.m
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import "JlViewController.h"


@implementation JlViewController{
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

- (void)refreshView
{
    NSLog(@"about to refresh UI:%@",[_app text]);
}

- (IBAction)toFirst:(id)sender
{
    [_app toFirst];
}

@end