-- Remove enum-based status
ALTER TABLE orders
DROP COLUMN status;

-- Add integer-backed status
ALTER TABLE orders
    ADD COLUMN status INT NOT NULL DEFAULT 1;

-- Optional: enforce valid values
ALTER TABLE orders
    ADD CONSTRAINT chk_orders_status
        CHECK (status IN (1, 2, 3));
