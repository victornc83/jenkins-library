#!/usr/bin/groovy

def call(namespace, body){
    podTemplate(name: 'maven',
      label: 'maven',
      cloud: 'openshift',
      namespace: '${namespace}',
      containers: [containerTemplate(name: 'maven', image: 'openshift/jenkins-slave-maven-centos7', ttyEnabled: true, workingDir: '/tmp', args: '${computer.jnlpmac} ${computer.name}')]
      ) {
        body()
      }
}
