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
    JpwordReader* reader;
}

- (void)setup {
    reader = &JpwordReader::getInstance();
//    self.impl->r = &JpwordReader::getInstance();
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
    //string text = reader->text();
    string text = [self impl]->r->text();
    NSString* nsText = [NSString stringWithUTF8String: text.c_str()];
    NSLog(@"about to refresh UIx:%@",nsText);
    //[[self wordDisplay] setText: @"XXXXX"];
    self.wordDisplay.text = nsText;
}

//- (IBAction)toFirst:(id)sender
//{
//    [_app toFirst];
//}

@end