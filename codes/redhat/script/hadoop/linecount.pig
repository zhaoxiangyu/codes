fs -ls $input
lines = LOAD '$input';
gropued_lines = GROUP lines ALL;
linecount = FOREACH gropued_lines GENERATE COUNT(lines);
DUMP linecount;
