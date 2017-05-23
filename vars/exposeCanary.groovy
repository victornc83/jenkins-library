#!/usr/bin/groovy

def call(project, name, service){
  loginOpenshift(project){
  }
}
