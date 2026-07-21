# Authentication Flow at NexaCorp

## Overview
NexaCorp uses a centralized authentication system to manage user identity and access
across all internal and customer-facing applications.

## Authentication Mechanism
- Users authenticate using company-issued credentials
- Single Sign-On (SSO) is enabled across internal systems
- Multi-Factor Authentication (MFA) is mandatory for sensitive operations

## Login Flow
1. User accesses an internal application
2. Request is redirected to the Authentication Service
3. Credentials are validated
4. MFA challenge is performed if required
5. Access token is issued upon successful authentication

## Token Management
- Access tokens are short-lived
- Refresh tokens are securely stored
- Tokens include user role and department metadata

## Common Issues
- Expired tokens causing access failures
- MFA challenges not completed
- Clock skew between services

## Related Policies
Refer to the Information Security Policy for password and MFA requirements.
