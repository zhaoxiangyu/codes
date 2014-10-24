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
}

    @property (nonatomic) struct Impl* impl;
    - (void)refreshView;
    - (IBAction)toFirst:(id)sender;
    - (IBAction)doBtnAbout:(id)sender;
@end
