generate(){
  test -d swingapp || mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart
}

eclipse(){
  pushd swingapp
  mvn eclipse:eclipse
  popd
}

if test 0 -eq $#; then
  echo command options: generate eclipse
else
  eval $1
fi
