#!/usr/bin/groovy

def call(project, new_project, image, version){
  loginOpenshift(project){
    sh "oc policy add-role-to-user system:image-puller system:serviceaccount:${project}:default -n ${new_project}"
    sh "oc tag ${project}/${image}:${version} ${new_project}/${image}:${version}"
  }
}
