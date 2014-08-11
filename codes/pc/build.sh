generate(){
  test -d $1 || mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart
}

eclipse(){
  test -d $1 && pushd $1
  if test 0 -eq $?; then
    mvn eclipse:eclipse
    popd
  else
    echo directory $1 not exists
  fi
}

if test 0 -eq $#; then
  echo command options: generate eclipse
else
  eval $1 $2
fi
