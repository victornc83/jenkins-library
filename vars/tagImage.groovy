#!/usr/bin/groovy

def call(project, image, version. new_version){
  loginOpenshift(project){
    sh "oc tag ${image}:${version} ${image}:${new_version}"
  }
}
