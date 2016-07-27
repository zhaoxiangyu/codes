# Ansible Role: cloudstack-agent

Install CloudStack Agent for KVM hypervisors.

## Requirements

- CentOS7 must Enable EPEL repo to install the vconfig package

## Role Variables

### `defaults/main.yml`

* `cloudstack_agent_tuned: false`
* `cloudstack_agent_tuned_profile: "virtual-host"`

## Dependencies

- cloudstack-common

## Example Playbook

    - hosts: servers
      vars:
        cloudstack_repo: "http://cloudstack.apt-get.eu/centos"
        cloudstack_ver: "4.4"
        cloudstack_agent_tuned: true
        cloudstack_agent_tuned_profile: "virtual-host"
      roles:
        - cloudstack-common
        - cloudstack-agent

## License

MIT / BSD

## Author Information

z@zstack.net
