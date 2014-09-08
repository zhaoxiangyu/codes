//
//  JlAppDelegate.h
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import <UIKit/UIKit.h>

@class JlViewController;

@interface JlAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) JlViewController *viewController;

- (void)chooseCourse:(int)courseNo;

- (void)toFirst;

@end
