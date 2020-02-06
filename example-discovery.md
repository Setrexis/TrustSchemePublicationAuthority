# lightest.nlnetlabs.nl demo

This is a "documentation-by-example" for our dev setup.

Since we only use one domain for the setup right now, `lightest.nlnetlabs.nl` act as a suffic for all domains/zones used.


## ATV perspective

0. Load Transaction (ASiC container)
    * -> Transaction data (XML?)
    * -> Signature
    * -> User cert (who signed container)
    * -> Issuer cert (who signed user cert)
    * -> etc. (delegation, ...)


1. Extract issuer domain
    - from user cert (Issuer Alternative Name)
    - from issuert cert (Subject Alternative Name) 
    * --> `a-trust.at.lightest.nlnetlabs.nl`


2. Query `_scheme._trust.a-trust.at.lightest.nlnetlabs.nl    IN    PTR`
    * --> `PTR signature.rtr.at.lightest.nlnetlabs.nl`

2.1 Query & verify `RRSIG` using `DNSKEY`


3. Query `_scheme._trust.signature.rtr.at.lightest.nlnetlabs.nl   IN    URI`
    * --> `URI https://signature.rtr.at.lightest.nlnetlabs.nl/currenttl.xml`

3.1 Query & verify `RRSIG` using `DNSKEY`


4. Download `https://signature.rtr.at.lightest.nlnetlabs.nl/currenttl.xml`

4.1 Verify trust list signature (using DANE)
    - by querying `TLSA` record for `signature.rtr.at.lightest.nlnetlabs.nl`
    - by querying `SMIMEA` record for `_scheme._trust.signature.rtr.at.lightest.nlnetlabs.nl`


5. Parse trust list & verify that ...
    * scheme is trusted (via policy)
    * issuer is part of scheme
    * issuer signed user
    * user signed transaction 

6. inject result into TPL interpreter scope


## Required DNS Entries

### For each Trust Service / Issuer

```
_scheme._trust.a-trust.at.lightest.nlnetlabs.nl    IN    PTR   signature.rtr.at.lightest.nlnetlabs.nl
```

+ DNSKEY, RRSIG records


### For the Trust Scheme

```
_scheme._trust.signature.rtr.at.lightest.nlnetlabs.nl   IN    URI   https://signature.rtr.at.lightest.nlnetlabs.nl/currenttl.xml

_scheme._trust.signature.rtr.at.lightest.nlnetlabs.nl   IN   SMIMEA   SMIMEA_record_data
```


+ DNSKEY, RRSIG records


## Required published Lists

* `https://signature.rtr.at.lightest.nlnetlabs.nl/currenttl.xml`