#!/usr/bin/groovy

def call(namespace, body){
    podTemplate(name: 'maven',
      label: 'maven',
      cloud: 'openshift',
      namespace: '${namespace}',
      containers: [containerTemplate(name: 'jnlp', image: 'openshift/jenkins-slave-maven-centos7', workingDir: '/tmp', args: '${computer.jnlpmac} ${computer.name}')]
      ) {
        node('maven'){
            body()
        }
      }
}
