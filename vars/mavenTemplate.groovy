#!/usr/bin/groovy

def call(namespace, body){
    podTemplate(label: 'maven',
      cloud: 'openshift',
      namespace: '${namespace}',
      containers: [containerTemplate(name: 'maven', image: 'openshift/jenkins-slave-maven-centos7', args: '${computer.jnlpmac} ${computer.name}')]
      ) {
        body()
      }
}
