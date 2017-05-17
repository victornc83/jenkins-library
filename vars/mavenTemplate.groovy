#!/usr/bin/groovy

def call(namespace){
    podTemplate(name: 'maven',
      label: 'maven',
      cloud: 'openshift',
      namespace: '${namespace}',
      containers: [containerTemplate(name: 'maven', image: 'openshift/jenkins-slave-maven-centos7', workingDir: '/tmp', args: '${computer.jnlpmac} ${computer.name}')]
      ) {
        node('maven'){
          body()
        }
      }
}
