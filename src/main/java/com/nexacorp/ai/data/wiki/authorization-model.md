# Authorization Model at NexaCorp

## Overview
Authorization at NexaCorp determines what authenticated users are allowed to access
within internal systems. Authorization decisions are based on roles, departments,
and business context.

## Role-Based Access Control (RBAC)
NexaCorp uses RBAC as the primary authorization mechanism. Common roles include:
- Engineering
- HR
- Support
- Operations
- Security

Each role has predefined access permissions to applications, data, and tools.

## Department-Based Constraints
In addition to roles, access may be constrained by department. For example:
- HR users can access employee records and HR policies
- Engineering users can access technical documentation and source repositories
- Support users can access customer-facing knowledge bases

## Privileged Access
Certain operations require elevated privileges:
- Access to production systems
- Viewing confidential security reports
- Managing user access and roles

Privileged access requires additional approval and is audited regularly.

## Authorization Tokens
Authorization information is embedded in access tokens issued during authentication.
Tokens typically include:
- User role
- Department
- Access scopes

## Common Authorization Issues
- Access denied due to missing role
- Stale permissions after role change
- Insufficient privileges for sensitive operations

## Related Documents
Refer to the IT Access Control Policy for detailed access request and review processes.
