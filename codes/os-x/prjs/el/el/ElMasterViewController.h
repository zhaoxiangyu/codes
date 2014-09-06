//
//  ElMasterViewController.h
//  el
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ElDetailViewController;

@interface ElMasterViewController : UITableViewController

@property (strong, nonatomic) ElDetailViewController *detailViewController;

@end
