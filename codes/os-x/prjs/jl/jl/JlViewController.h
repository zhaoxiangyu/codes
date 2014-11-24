//
//  JlViewController.h
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JlAppDelegate.h"

@interface JlViewController : UIViewController {
    IBOutlet UILabel *wordDisplay;
    __weak IBOutlet UIView *wordDisplayView;
}

@property (nonatomic) struct Impl* impl;

- (void)refreshView;
- (IBAction)toFirst:(id)sender;
- (IBAction)toEnd:(id)sender;

- (IBAction)chooseCourse:(id)sender;
- (IBAction)doBtnAbout:(id)sender;

- (IBAction)showAllLevel:(id)sender;
- (IBAction)showLevelOne:(id)sender;
- (IBAction)showLevelTwo:(id)sender;
- (IBAction)showLevelThree:(id)sender;
- (IBAction)showLevelFour:(id)sender;
- (IBAction)showLevelFive:(id)sender;
@end
