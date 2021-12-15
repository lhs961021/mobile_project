# mobile_project
모바일소프트웨어 프로젝트_요소수 재고어플

## 주제: 요소수 재고 어플

## 구현 기술 : java를 기반으로 android studio로 제작

## 구현 방법

공공데이터 open api 사용 (https://www.data.go.kr/data/15095040/openapi.do)

네이버지도 api 사용 

### 2.3. 외부 라이브러리의 사용

본 애플리케이션을 구현하는 과정에서 활용 가능한 외부 라이브러리는 제한없이 사용하는 것을 원칙으로 한다.

#### 2.3.1. Requirements.txt

본 애플리케이션의 소스코드 내에서 활용한 모든 외부 라이브러리는 `requirements_***.txt`에 해당 라이브러리 리스트를 저장하여 팀원들에게 공유하여야 한다.

```
$ pip freeze > requirements_cpu.txt   // CPU 전용 컴퓨터
$ pip freeze > requirements_cuda11.txt   // CUDA Toolkit 11 버전이 설치된 컴퓨터
```

`requirements_***.txt`에 저장된 외부 라이브러리를 다운로드 받는 명령어는 다음과 같다.

```
$ pip install -r requirements_cpu.txt   // CPU 전용 컴퓨터
$ pip install -r requirements_cuda11.txt   // CUDA Toolkit 11 버전이 설치된 컴퓨터
```

### 2.4. Python 애플리케이션 실행

```
$ python app.py [모델] [훈련/검증]
```

### 2.5. Python 가상환경

#### 2.5.1. 주요 가상환경 프로그램

1. [Anaconda](https://www.anaconda.com/products/individual): Anaconda Inc.에서 개발한 데이터과학용 Python 가상환경 프로그램
2. [Miniconda](https://docs.conda.io/en/latest/miniconda.html): Anaconda Inc.에서 개발한 데이터과학용 Python 가상환경 프로그램 (Anaconda의 최소 설치 버전)
3. [pyenv](https://github.com/pyenv/pyenv) (+ [pyenv-virtualenv](https://github.com/pyenv/pyenv-virtualenv)): UNIX 기반의 Python 버전 관리 및 가상환경 구현용 프로그램

#### 2.5.2. 비교

|                       | Anaconda | Miniconda | pyenv |
| :-------------------: | :------: | :-------: | :---: |
| **사용자 인터페이스** |   ---    |    ---    |  ---  |
|          GUI          |    O     |     O     |   O   |
|         Bash          |          |     O     |   O   |
|          Zsh          |          |     O     |   O   |
|     **운영체제**      |   ---    |    ---    |  ---  |
|        Windows        |    O     |     O     |       |
|          WSL          |          |     O     |   O   |
|         MacOS         |    O     |     O     |   O   |
|         Linux         |    O     |     O     |   O   |

##### **표 1.** 가상환경 프로그램 별 차이점 비교

## 3. 분류모델 성능평가

분류모델의 성능평가는 **Macro F1 Score**를 사용하여 분류성능을 평가한다.

- True Positive (TP): 실제 **참**인 값을 **참**으로 예측한 결과의 집합
- True Negative (TN): 실제 **거짓**인 값을 **거짓**으로 예측한 결과의 집합
- False Positive (FP): 실제 **거짓**인 값을 **참**으로 예측한 결과의 집합
- False Negative (FN): 실제 **참**인 값을 **거짓**으로 예측한 결과의 집합

|               | 실제 참 | 실제 거짓 |
| :-----------: | :-----: | :-------: |
|  **예측 참**  |   TP    |    FP     |
| **예측 거짓** |   FN    |    FP     |

##### **표 2.** Confusion Matrix

정밀도 (Precision)

- 정의
- _Precision = TP / (TP + FP)_

재현율 (Recall)

- 정의
- _Recall = TP / (TP + TN)_

Macro F1 Score

- 정의
- _F1 score = 2 x (precision x recall) / (precision + recall)_

## 4. 환경변수 파일

.gitignore

- Git 관련 환경변수 파일

requirements.txt

- 파이썬 라이브러리 종속성 파일
