#!/usr/bin/groovy

def call(){
  def matcher = readFile('pom.xml') =~ '<artifactId>(.+)</artifactId>'
  matcher ? matcher[0][1] : null
}
