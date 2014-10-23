//
//  JlAppDelegate.m
//  jl
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import "JlAppDelegate.h"

#import "JlViewController.h"
#import "core/JpwordReader.h"
#import "core/port/ReaderEventListener.h"

struct ListenerImpl{
    JlViewController* viewController;
};

@implementation JlAppDelegate{
    JpwordReader* reader;
    ReaderEventListener* listener;
}

void uncaughtExceptionHandler(NSException *exception) {
    NSLog(@"CRASH: %@", exception);
    NSLog(@"Stack Trace: %@", [exception callStackSymbols]);
    // Internal error reporting
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    NSSetUncaughtExceptionHandler(&uncaughtExceptionHandler);

    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.

    //NSString * mp3BundlePath = [[NSBundle mainBundle] pathForResource:@"audios" ofType:@"bundle"];
    //NSBundle * mp3Bundle = [NSBundle bundleWithPath:mp3BundlePath];
    //NSString * mp3Path = [mp3Bundle pathForResource:@"unit1\\1\\～ちゅん" ofType:@"mp3" inDirectory:@"jw/course1"];
    //NSLog(@"%@", mp3Path);
    //NSArray * mp3Array = [mp3Bundle pathsForResourcesOfType:@"mp3" inDirectory:@"jw/course1"];
    //for (NSString * mp3Path in mp3Array) {
    //    NSLog(@"%@", mp3Path);
    //}

    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        self.viewController = [[JlViewController alloc] initWithNibName:@"JlViewController_iPhone" bundle:nil];
    } else {
        self.viewController = [[JlViewController alloc] initWithNibName:@"JlViewController_iPad" bundle:nil];
    }
    self.viewController.app = self;
    setupCore(self,self.viewController);
    
    [self.viewController setValue: self forKey: @"app"];
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    return YES;
}

void setupCore(JlAppDelegate* app,JlViewController* viewController){
    
    app->reader = new JpwordReader();
    app->listener = new ReaderEventListener();
    app->listener->impl->viewController = viewController;
    
    app->reader->listener = app->listener;
    app->reader->start();
}

- (void)chooseCourse:(int)courseNo
{
    reader->chooseCourse(courseNo);
}

- (void)toFirst
{
    reader->toBeginning();
}

- (NSString*)text
{
    string text = reader->text();
    return [NSString stringWithUTF8String: text.c_str()];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    reader->quit();
    delete listener;
    delete reader;
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
