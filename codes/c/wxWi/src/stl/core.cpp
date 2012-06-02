#include <iostream>
#include <vector>
#include <string>
#include "../data/sample.h"

//using std::cout;
//using std::endl;
//using std::vector;
//using std::string;
using namespace std;

int another_main() {
    vector<string> vs;
    vs.push_back("a");
    sample s;
    s.id = 1;

    cout << "loop by index:" << endl;
    for(unsigned int i=0; i <vs.size(); i++) {
        cout << vs[i] << endl << s.id << endl;
    }

    return 0;
}
