-- Default SaaS Plans
INSERT INTO plans (name, price, features) VALUES ('Basic', 29.00, 'Up to 50 active members, Basic class scheduling, Standard email support');
INSERT INTO plans (name, price, features) VALUES ('Pro', 99.00, 'Up to 500 active members, Advanced scheduling, Automated billing, priority support');
INSERT INTO plans (name, price, features) VALUES ('Enterprise', 299.00, 'Unlimited members, Multi-location management, Custom branded app, Open API access');

-- Global Integrations
INSERT INTO integrations (name, is_active) VALUES ('WhatsApp Gateway', true);
INSERT INTO integrations (name, is_active) VALUES ('Stripe Payments', true);
INSERT INTO integrations (name, is_active) VALUES ('SMS Alerts (Twilio)', true);
INSERT INTO integrations (name, is_active) VALUES ('Zoom Meetings', true);
INSERT INTO integrations (name, is_active) VALUES ('Mail Engine', true);
INSERT INTO integrations (name, is_active) VALUES ('Door Access API', false);
