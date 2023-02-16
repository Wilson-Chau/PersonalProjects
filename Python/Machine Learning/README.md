# Mortality Prediction (classification) and Length-of-stay Prediction (regression)

### Background

Electronic Health Records (EHRs) holds the potential to improve healthcare in many ways, such as delivering
better patient treatments, improving hospital operations, and answering fundamental scientific questions. As the
development of modern healthcare systems, a promising application of EHRs is designing machine learning models
for risk prediction tasks, like mortality and long Length-of-stay (LOS) prediction.

### Dataset description

#### Overview

MIMIC-III is a well-known public de-identified EHR database, which contains information of 53423 patients
admitted to critical care units at a Boston-area hospital from 2001-2012.The dataset is transformed from MIMIC-III database. Specifically, the input features are hourly aggregated time-varying vitals and labs (hourly mean, count and standard deviation).

- The index for each sample has the format: {subject*id}*{hadm*id}*{icustay_id}.
- Labs means laboratory test results (for example, hematology, chemistry, and microbiology results).
- Vital sign means patients' information routinely monitored by medical professionals and health care providers, including: heart rate, mean blood pressure, oxygen saturation, etc.

# More details

This dataset is preprocessed following the MIMIC Extract pipeline. More details could be found in the original paper: https://arxiv.org/pdf/1907.08322.pdf.
