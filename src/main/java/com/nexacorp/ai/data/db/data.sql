
-- Seed data for NexaCorp Internal Knowledge Database

-- FAQs
INSERT INTO faqs (question, answer, department, visibility) VALUES
('How many paid leave days do employees get per year?', 'Employees are entitled to 24 days of paid leave per calendar year.', 'HR', 'INTERNAL'),
('How do I apply for extended medical leave?', 'Extended medical leave requires HR approval and submission of a medical certificate.', 'HR', 'INTERNAL'),
('How can I reset my VPN password?', 'VPN passwords can be reset using the internal IT portal or by contacting IT Support.', 'IT', 'INTERNAL'),
('Is MFA required for internal applications?', 'Yes, multi-factor authentication is mandatory for accessing internal systems.', 'IT', 'INTERNAL'),
('Who can access production systems?', 'Only authorized engineering and operations personnel with elevated privileges can access production systems.', 'Engineering', 'RESTRICTED'),
('Where can I find API design standards?', 'API design standards are documented in the internal API Guidelines wiki.', 'Engineering', 'INTERNAL'),
('How do I report a security incident?', 'Security incidents must be reported to the Information Security team within 24 hours.', 'Security', 'INTERNAL'),
('What should I do if I get an access denied error?', 'Verify your role and department, and ensure access has been approved.', 'IT', 'INTERNAL'),
('How often are access rights reviewed?', 'Access rights are reviewed periodically as part of regular audits.', 'Security', 'INTERNAL'),
('Who do I contact for onboarding issues?', 'Contact HR for policy questions and IT Support for system access issues.', 'HR', 'INTERNAL');

-- Release Notes
INSERT INTO release_notes (version, summary, details, release_date) VALUES
('v2.1', 'Improved authentication performance', 'Optimized token validation and reduced login latency.', '2024-01-15'),
('v2.2', 'Enhanced role-based access control', 'Introduced finer-grained permissions for internal tools.', '2024-02-10'),
('v2.3', 'API Gateway upgrade', 'Upgraded API Gateway to support higher throughput and better monitoring.', '2024-03-05'),
('v2.4', 'Security patch release', 'Applied critical security patches across all services.', '2024-03-25'),
('v2.5', 'Improved logging and monitoring', 'Enhanced centralized logging and alerting capabilities.', '2024-04-12'),
('v2.6', 'New deployment pipeline', 'Rolled out automated CI/CD pipelines for faster deployments.', '2024-05-02'),
('v2.7', 'Data privacy enhancements', 'Updated data handling mechanisms to align with privacy guidelines.', '2024-05-20');

-- Announcements (Email-style)
INSERT INTO announcements (subject, body, category, effective_from, effective_to, source_type) VALUES
('Updated Work From Home Policy', 'The work from home policy has been updated. Employees are now required to work from the office at least two days per week.', 'HR', '2023-01-01', '2024-03-31', 'EMAIL'),
('Revised Work From Home Policy', 'Effective April 2024, employees may work remotely up to three days per week, subject to manager approval.', 'HR', '2024-04-01', NULL, 'EMAIL'),
('Mandatory MFA Rollout', 'Multi-factor authentication will be mandatory for all internal applications starting March 2024.', 'IT', '2024-03-01', NULL, 'EMAIL'),
('Scheduled Security Maintenance', 'Security maintenance is scheduled for April 10th. Some services may be unavailable.', 'Security', '2024-04-10', '2024-04-10', 'EMAIL'),
('New Data Privacy Guidelines', 'Updated data privacy guidelines have been published. All employees must review the new policies.', 'Legal', '2024-05-01', NULL, 'EMAIL'),
('Access Review Reminder', 'Quarterly access reviews will be conducted. Managers must review team access rights.', 'Security', '2024-02-01', NULL, 'EMAIL');
