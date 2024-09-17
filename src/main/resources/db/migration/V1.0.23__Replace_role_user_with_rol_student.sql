-- Step 1: Insert ROLE_STUDENT into the authority table
INSERT INTO public.authority (name)
VALUES ('ROLE_STUDENT')
ON CONFLICT (name) DO NOTHING;

-- Step 2: Update user_authority table to replace ROLE_USER with ROLE_STUDENT
UPDATE public.user_authority
SET authority_name = 'ROLE_STUDENT'
WHERE authority_name = 'ROLE_USER';

-- Step 3: Delete ROLE_USER from the authority table
DELETE
FROM public.authority
WHERE name = 'ROLE_USER';