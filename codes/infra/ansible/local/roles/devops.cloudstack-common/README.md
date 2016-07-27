# Ansible Role: cloudstack-common

Install Apache Cloudstack Common.

## Requirements

None.

## Role Variables

### `defaults/main.yml`

* `cloudstack_repo: "http://cloudstack.apt-get.eu/centos"`
* `cloudstack_ver: "4.4"`

## Dependencies

None.

## Example Playbook

    - hosts: servers
      vars:
        cloudstack_repo: "http://cloudstack.apt-get.eu/centos"
        cloudstack_ver: "4.4"
      roles:
         - cloudstack-common

## License

MIT / BSD

## Author Information

z@zstack.net
